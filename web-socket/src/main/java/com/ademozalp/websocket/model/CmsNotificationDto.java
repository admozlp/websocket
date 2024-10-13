package com.ademozalp.websocket.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsNotificationDto {
    private String employeeId;
    private String message;
    private String contentId;
    private boolean readStatus;
    private String notificationType;
    private List<String> rolesList;
}
