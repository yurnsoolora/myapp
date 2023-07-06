package bitcamp.myapp.handler;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;

public class MemberUpdateListener implements MemberActionListener {

  MemberDao memberDao;

  public MemberUpdateListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int memberNo = prompt.inputInt("번호? ");

    Member m = memberDao.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    m.setName(prompt.inputString("이름(%s)? ", m.getName()));
    m.setAge(prompt.inputInt("나이(%d)? ", m.getAge()));
    m.setHeight(prompt.inputInt("키(%d)? ", m.getHeight()));
    m.setWeight(prompt.inputInt("몸무게(%d)? ", m.getWeight()));
    m.setGender(MemberActionListener.inputGender(m.getGender(), prompt));
    m.setPassword(prompt.inputString("새암호? "));

    memberDao.update(m);
  }

}
