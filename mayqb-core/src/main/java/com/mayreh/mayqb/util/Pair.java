package com.mayreh.mayqb.util;

import lombok.Value;

/**
 * Represents a key value pair
 */
@Value(staticConstructor = "of")
public class Pair<K, V> {

    K key;

    V value;
}
