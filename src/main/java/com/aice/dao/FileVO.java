package com.aice.dao;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVO extends DaoDtUser implements Serializable{
    MultipartFile pathFile;
}