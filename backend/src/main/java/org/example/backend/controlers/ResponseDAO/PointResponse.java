package org.example.backend.controlers.ResponseDAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.data.entity.Point;
import org.example.backend.data.entity.User;

import java.util.Date;
import java.util.Objects;

@Setter
@Getter
public class PointResponse {
    private Float x;
    private Float y;
    private Float z;
    private Boolean result;
    private long time;
    private Date date;
    private String message;

    private PointResponse(Point point) {
        this.x = point.getX();
        this.y = point.getY();
        this.z = point.getZ();
        this.result = point.isResult();
        this.time = point.getTime();
        this.date = point.getDate();
        this.message = "ok";
    }

    public PointResponse(String message) {
        this.message = message;
    }

    public static PointResponse from(Point point) {
        PointResponse test = new PointResponse(point);
        System.out.println(test);
        return test;
    }
    public static PointResponse from(String message) {
        PointResponse test = new PointResponse(message);
        System.out.println(test);
        return test;
    }

    @Override
    public String toString() {
        return "PointResponse{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", result=" + result +
                ", time=" + time +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
