package com.cos.instagram.domain.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowingListRespDto {

	private int id;
	private String name;
	private String username;
	private String followState;
}
