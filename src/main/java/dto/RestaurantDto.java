package dto;

import lombok.Data;

@Data
public class RestaurantDto {

    private Integer restId;
    private String restName;
    private String restAddress;
    private boolean restOpen;

}
