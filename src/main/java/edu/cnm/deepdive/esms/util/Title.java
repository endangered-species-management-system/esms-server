package edu.cnm.deepdive.esms.util;

public enum Title {

  APPRENTICE(1),
  JUNIOR_RESEARCHER(2),
  RESEARCHER(3),
  PROJECT_LEAD(4),
  DEPARTMENT_HEAD(5);

  private int code;

  Title(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
