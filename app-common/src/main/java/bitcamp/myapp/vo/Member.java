package bitcamp.myapp.vo;

import java.io.Serializable;

public class Member implements Serializable, CsvObject, AutoIncrement {
  private static final long serialVersionUID = 1L;

  public static int userId = 1;

  public static final char MALE = 'M';
  public static final char FEMALE = 'W';

  private int no;
  private String name;
  private int age;

  private int height;

  private int weight;
  private String password;
  private char gender;

  private double bmi;

  public Member() {}

  public Member(int no) {
    this.no = no;
  }

  public double calculateBMI() { //bmi계산
    double heightInMeters = this.height / 100.0;
    double bmi;

    if (this.gender == MALE) {
      bmi = this.weight / (heightInMeters * heightInMeters);
    } else if (this.gender == FEMALE) {
      bmi = (this.weight + 0.9) / (heightInMeters * heightInMeters);
    } else {
      // 성별이 지정되지 않은 경우 예외 처리
      throw new IllegalStateException("Gender not specified");
    }

    setBmi(bmi); // BMI 값을 설정
//    System.out.printf("회원님의 비만도(BMI)는 %.2f 입니다.", bmi);
//    System.out.println();
    return bmi;
  }

  public static Member fromCsv(String csv) {
    String[] values = csv.split(",");

    Member member = new Member(Integer.parseInt(values[0]));
    member.setName(values[1]);
    member.setAge(Integer.parseInt(values[2]));
    member.setHeight(Integer.parseInt(values[3]));
    member.setWeight(Integer.parseInt(values[4]));
    member.setGender(values[5].charAt(0));
    member.setBmi(Double.parseDouble(values[6]));
    member.setPassword(values[7]);

    if (Member.userId <= member.getNo()) {
      Member.userId = member.getNo() + 1;
    }

    return member;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%d,%d,%d,%c,%.2f,%s",
            this.getNo(),
            this.getName(),
            this.getAge(),
            this.getHeight(),
            this.getWeight(),
            this.getGender(),
            this.getBmi(),
            this.getPassword());

  }

  @Override
  public void updateKey() {
    if (Member.userId <= this.no) {
      Member.userId = this.no + 1;
    }
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    Member m = (Member) obj;
    if (this.getNo() != m.getNo()) {
      return false;
    }
    return true;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public char getGender() {
    return gender;
  }
  public void setGender(char gender) {
    this.gender = gender;
  }

  public double getBmi() {
    return calculateBMI();
  }

  public void setBmi(double bmi) {
    this.bmi = bmi;
  }
}