package com.example.QRCodeService;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import com.google.zxing.*;



@RestController
public class TaskController {

    public ResponseEntity<?> BadResponseCreator(String errorMsg) {
        return ResponseEntity
                .badRequest()
                .body(errorMsg);
    }

    /*public ResponseEntity<?> createQRCode(String contents, Integer size,String type, String correction) throws WriterException {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            Map<EncodeHintType, ?> hint = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf(correction.toUpperCase()));

            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hint);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf("IMAGE_" + type.toUpperCase()))
                    .body(image);

        } catch (WriterException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("error while creating the code");
        }
    }*/

    @GetMapping("/api/health")
    public ResponseEntity<?> getHealthOK() {
        return new ResponseEntity<>(HttpStatus.OK);
    } //works

    @GetMapping("api/qrcode")
    public ResponseEntity<?> getQrCode(@RequestParam(required = true) String contents,
                                       @RequestParam(required = false, defaultValue = "250") Integer size,
                                       @RequestParam(required = false, defaultValue = "PNG") String type,
                                       @RequestParam(required = false, defaultValue = "L") String correction) throws IOException, WriterException {
        try {
            if (contents.isBlank()) {
                return BadResponseCreator("""
                            {
                                "error": "Contents cannot be null or blank"
                            }""");
            } else if (size !=  null && (size < 150 || size > 350)) {
                return BadResponseCreator("""
                            {
                              "error": "Image size must be between 150 and 350 pixels"
                            }""");
            } else if (!correction.isBlank() && (!correction.equalsIgnoreCase("l") &&
                    !correction.equalsIgnoreCase("m") &&
                    !correction.equalsIgnoreCase("q") &&
                    !correction.equalsIgnoreCase("h"))) {
                return BadResponseCreator("""
                    {
                        "error": "Permitted error correction levels are L, M, Q, H"
                    }
                    """);
            } else if (!type.equalsIgnoreCase("png") && !type.equalsIgnoreCase("jpeg") && !type.equalsIgnoreCase("gif")) {
                return BadResponseCreator("""
                            {
                                "error": "Only png, jpeg and gif image types are supported"
                            }""");
            } else {
                try {
                    QRCodeWriter writer = new QRCodeWriter();
                    Map<EncodeHintType, ?> hint = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf(correction.toUpperCase()));
                    BitMatrix matrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hint);
                    BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

                    return ResponseEntity
                            .ok()
                            .contentType(MediaType.valueOf("image/" + type.toLowerCase()))
                            .body(image);
                } catch (WriterException e) {
                    return ResponseEntity
                            .internalServerError()
                            .body("error while creating the code");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("An error occurred while processing the request");
        }

    }
}

