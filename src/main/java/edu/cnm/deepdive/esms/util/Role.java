package edu.cnm.deepdive.esms.util;

public enum Role {

  INTERN(1),
  RESEARCHER(2),
  LEAD(3),
  ADMINISTRATOR(4);

  private int code;

  Role(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
