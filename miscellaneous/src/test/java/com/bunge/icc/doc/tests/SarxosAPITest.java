package com.bunge.icc.doc.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class SarxosAPITest {
	public static void main(String[] args) {
		Webcam webcam = Webcam.getDefault();
		if (webcam != null) {
			System.out.println("Webcam: " + webcam.getName());
			webcam.open();
			BufferedImage image = webcam.getImage();
			
			try {
				ImageIO.write(image, "jpeg", new File("test.jpeg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No webcam detected");
		}
	}
}
