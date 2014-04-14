package cn.kane.freader;

import java.nio.ByteBuffer;

public interface Decoder<T> {
	
    public T decode(ByteBuffer buffer);
    
}