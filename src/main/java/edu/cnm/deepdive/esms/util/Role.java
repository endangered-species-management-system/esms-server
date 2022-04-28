package edu.cnm.deepdive.esms.util;

public enum Role {

  RESEARCHER(1),
  LEAD(2),
  ADMINISTRATOR(4);

  private final int code;

  Role(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
