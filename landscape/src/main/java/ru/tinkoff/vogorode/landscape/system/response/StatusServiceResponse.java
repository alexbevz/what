package ru.tinkoff.vogorode.landscape.system.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StatusServiceResponse {

    private String host;

    private String status;

    private String artifact;

    private String name;

    private String group;

    private String version;
}
