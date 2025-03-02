package com.kyu.pappy.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 가압된 이메일입니다.");

    /*
    enum의 각요소는 기본적으로 객체처럼 동작, 생성자를 통해 추가적인 정보를 저장 할수 있다.
    enum은 단순한 상수뿐만이 아닌 필드와생성자를 가질수있음
    () 안의 값들은 생성자로 전달되어 내부 상태를 설정하는 역할을 함
     */
    private final HttpStatus status;
    private final String message;


    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
