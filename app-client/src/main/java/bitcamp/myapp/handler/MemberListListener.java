package bitcamp.myapp.handler;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

import java.util.List;

public class MemberListListener implements ActionListener {

  MemberDao memberDao;

  public MemberListListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("---------------------------------------");
    System.out.println("번호, 이름, 나이, 키, 몸무게, 성별, bmi");
    System.out.println("---------------------------------------");

    List<Member> list = memberDao.list();
    for (Member m : list) {
      System.out.printf("%d, %s, %d, %d, %d, %s, %.2f\n",
              m.getNo(), m.getName(), m.getAge(), m.getHeight(), m.getWeight(),
          m.getGender() == 'M' ? "남성" : "여성", m.getBmi());
    }
  }

}
