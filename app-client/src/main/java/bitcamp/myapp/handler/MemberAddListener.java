package bitcamp.myapp.handler;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;

public class MemberAddListener implements MemberActionListener {

  MemberDao memberDao;

  public MemberAddListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Member m = new Member();
    m.setName(prompt.inputString("이름? "));
    m.setAge(prompt.inputInt("나이? "));
    m.setHeight(prompt.inputInt("키? "));
    m.setWeight(prompt.inputInt("몸무게? "));
    m.setGender(MemberActionListener.inputGender((char)0, prompt));
    m.calculateBMI();
    m.setPassword(prompt.inputString("암호? "));

    memberDao.insert(m);
  }
}
