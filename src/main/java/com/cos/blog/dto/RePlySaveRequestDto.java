package com.cos.blog.dto;

import lombok.Data;

@Data
public class RePlySaveRequestDto {
	private int userId;
	private int boardId;
	private String contentString;
}
