package com.graduation.mangaka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.SettingDefinition;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudinaryDTO {

    @JsonProperty("asset_id")
    private String assetId;

    @JsonProperty("public_id")
    private String publicId;


    @JsonProperty("version_id")
    private String versionId;

    private String signature;
    private int width;
    private int height;
    private String format;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("created_at")
    private String createdAt;

    private List<String> tags;
    private String type;
    private String etag;
    private boolean placeholder;
    private String url;

    @JsonProperty("secure_url")
    private String secureUrl;

    @JsonProperty("asset_folder")
    private String assetFolder;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("original_filename")
    private String originalFilename;

    @JsonProperty("api_key")
    private String apiKey;
}
