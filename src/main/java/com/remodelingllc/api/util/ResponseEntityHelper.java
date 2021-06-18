package com.remodelingllc.api.util;

import org.springframework.http.*;

public class ResponseEntityHelper {

    public static ResponseEntity<byte[]> responseForFile(final byte[] file,
                                                         final MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.setContentDisposition(ContentDisposition.builder("inline").build());
        headers.setContentType(mediaType);
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

}
