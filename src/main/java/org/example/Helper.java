package org.example;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class Helper {
    public static byte[] getBinaryUUID(UUID input){
        byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(input.getMostSignificantBits())
                .putLong(input.getLeastSignificantBits());

        return uuidBytes;
    }
}
