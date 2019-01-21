package ru.titov.s04.service;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestService {

    public String hash(String str) {
        return DigestUtils.md5Hex(str);
    }
}
