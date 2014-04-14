package cn.kane.intercept;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.constraints.Length;

import cn.kane.groups.ValidatorGroup4Add;
import cn.kane.groups.ValidatorGroup4Update;

public class InputParamsValidIntercept {

	private static Validator validator;
	private boolean isValidateBasicParam = false ;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public void valid(JoinPoint joinPoint) throws Exception{
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs() ;
		if(null!=args){
			// group
			Class<?> validGroup = null ;
			if(methodName.startsWith("save")){
				validGroup = ValidatorGroup4Add.class ;
			}else if(methodName.startsWith("update")){
				validGroup = ValidatorGroup4Update.class ;
			}else{
				validGroup = Default.class ;
			}
			//validate
			StringBuffer errMsg = new StringBuffer() ;
			for(Object arg : args){
				if(null!=arg){
					this.validWraperParam(arg, validGroup, errMsg) ;
				}
			}
			// error messages
			if(!"".equals(errMsg.toString().trim())){
				throw new Exception(errMsg.toString()) ;
			}
		}
	}
	
	public void validateExtender(JoinPoint joinPoint) throws Exception{
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs() ;
		if(null!=args){
			// group
			Class<?> validGroup = null ;
			if(methodName.startsWith("save")){
				validGroup = ValidatorGroup4Add.class ;
			}else if(methodName.startsWith("update")){
				validGroup = ValidatorGroup4Update.class ;
			}else{
				validGroup = Default.class ;
			}
			// target method
			MethodSignature signature = (MethodSignature)joinPoint.getSignature() ;
			Method targetMthd = signature.getMethod() ;
			if(null == targetMthd){
				throw new Exception("no suit method with args");
			}
			Annotation[][] annos = null ;
			Class<?>[] paramTypes = targetMthd.getParameterTypes() ;
			if(isValidateBasicParam){
				annos = targetMthd.getParameterAnnotations() ;
			}
			// valid
			StringBuffer errMsg = new StringBuffer() ;
			int pos = 0 ;
			for(Object arg : args){
				Class<?> clazz = paramTypes[pos] ;
				if(!this.isPrimitive(clazz)){// wrapper
					this.validWraperParam(arg, validGroup, errMsg) ;
				}else{//basic
					if(!isValidateBasicParam){
						continue ;
					}else{
						Annotation[] annosOnParam = annos[pos] ;
						this.validBasicParam(annosOnParam, arg, validGroup, errMsg) ;
					}
				}
				pos ++ ;
			}
			// error messages
			if(!"".equals(errMsg.toString().trim())){
				throw new Exception(errMsg.toString()) ;
			}
		}
		
	}
	
	private boolean isPrimitive(Class<?> clazz){
		boolean isPrimitive = false ;
		if(clazz.isPrimitive()){
			isPrimitive = true ;
		}
		if(clazz == Integer.class
				|| clazz == Short.class
				|| clazz == Long.class
				|| clazz == Byte.class
				|| clazz == Double.class
				|| clazz == Float.class
				|| clazz == Character.class
				|| clazz == Boolean.class
				|| clazz == String.class
				){
			isPrimitive = true ;
		}
		return isPrimitive ;
	}
	
	private void validWraperParam(Object arg,Class<?> validGroup,StringBuffer errMsg){
		Set<ConstraintViolation<Object>> validResults = validator.validate(arg, validGroup);
		if(null!=validResults){
			for(ConstraintViolation<Object> violt : validResults){
				if(null!=violt){
					if(null!=violt.getMessage()){
						errMsg.append(violt.getPropertyPath().toString()).append(violt.getMessage()).append(",");
					}
				}
			}
		}
	}
	
	private void validBasicParam(Annotation[] paramAnnos,Object arg,Class<?> validGroup,StringBuffer errMsg){
		if(null!=paramAnnos){
			for(Annotation anno : paramAnnos){
				//TODO valid by annotation
				Length length = (Length)anno ;
				Class< ? extends Payload>[] payloads = length.payload() ;
				try {
					Payload payload = (Payload)payloads[0].newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean getIsValidateBasicParam() {
		return isValidateBasicParam;
	}

	public void setIsValidateBasicParam(boolean isValidateBasicParam) {
		this.isValidateBasicParam = isValidateBasicParam;
	}
}
