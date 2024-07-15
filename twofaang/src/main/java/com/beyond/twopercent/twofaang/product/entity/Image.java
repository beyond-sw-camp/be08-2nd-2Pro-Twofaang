package com.beyond.twopercent.twofaang.product.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;                // 이미지 식별 번호

    @Column(name = "file_name")
    private String fileName;            // 이미지 파일 이름

    @Column(name = "file_path")
    private String filePath;            // 이미지 디렉터리 경로

    @Column(name = "product_id")
    private int productId;              // 상품 식별 번호
}
