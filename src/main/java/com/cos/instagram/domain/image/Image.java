package com.cos.instagram.domain.image;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instagram.domain.tag.Tag;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserProfileImageRespDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SqlResultSetMapping(
		name = "UserProfileImageRespDtoMapping", 
		classes = @ConstructorResult(
				targetClass = UserProfileImageRespDto.class, 
				columns = {
		// 통계같은것 짤때 씀
		@ColumnResult(name = "id", type = Integer.class), 
		@ColumnResult(name = "imageUrl", type = String.class),
		@ColumnResult(name = "likeCount", type = Integer.class),
		@ColumnResult(name = "commentCount", type = Integer.class) 
		}
	)
)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String location;
	private String caption;
	private String imageUrl;

	// private int userId; Mybatis는 userId를 통해서 JOIN을 하게 되는데
	@ManyToOne(fetch = FetchType.EAGER) // Image를 select하면 한명의 User가 딸려옴. ->기본적인 전략이 (fetch = FetchType.EAGER)
	@JoinColumn(name = "userId") // 컬럼명 DB에서는 userId라고 쓰게됨
	private User user; // 타입은 User오브젝트의 PK의 타입
	// 해당 오브젝트를 SELECT 해서 들고 와준다.(내부적으로는 JOIN을 해줌)

	// Image를 select하면 여러개의 tag가 딸려옴.
	@OneToMany(mappedBy = "image", fetch = FetchType.LAZY) // 규칙 - 연관관계 주인의 변수명을 적는다. 나는 FK가 아니다를 적어줘야함
	@JsonIgnoreProperties({ "image" })
	private List<Tag> tags;
	// Insert할때 태그들을 한번에 넣는데 FK가 되면 안됨.

	// 요즘은 타임스탬프보다 로컬데이트를 많이씀
	@CreationTimestamp
	private Timestamp createDate;

}
