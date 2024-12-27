package com.makar.tenant.user;

import java.io.Serializable;

public record UserId(
        Long id,
        UserLookupTable table
) implements Serializable {

    public String asString() {
        return table.name() + ":" + id;
    }

    public static UserId fromString(String value) {
        var parts = value.split(":");
        return new UserId(Long.valueOf(parts[1]), UserLookupTable.valueOf(parts[0]));
    }

    /**
     * @deprecated Use {@link #asString()} instead.
     * <p> This method is overridden to avoid confusion with {@link #asString()}.
     */
    @Override
    @Deprecated(since = "1.0")
    public String toString() {
        return asString();
    }

}
