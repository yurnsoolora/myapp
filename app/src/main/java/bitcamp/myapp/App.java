package bitcamp.myapp;

import java.util.LinkedList;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardListDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.MemberListDao;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.FooterListener;
import bitcamp.myapp.handler.HeaderListener;
import bitcamp.myapp.handler.HelloListener;
import bitcamp.myapp.handler.MemberAddListener;
import bitcamp.myapp.handler.MemberDeleteListener;
import bitcamp.myapp.handler.MemberDetailListener;
import bitcamp.myapp.handler.MemberListListener;
import bitcamp.myapp.handler.MemberUpdateListener;
import bitcamp.myapp.vo.Board;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class App {

  MemberDao memberDao = new MemberListDao("member.json");
  BoardDao boardDao = new BoardListDao("board.json");

  LinkedList<Board> boardList = new LinkedList<>();

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public App() {
    prepareMenu();
  }

  public static void main(String[] args) {
    new App().execute();
  }

  static void printTitle() {
    System.out.println("나의 식단 관리 시스템");
    System.out.println("----------------------------------");
  }

  public void execute() {
    printTitle();
    mainMenu.execute(prompt);
    prompt.close();
  }

  private void prepareMenu() {
    MenuGroup memberMenu = new MenuGroup("회원관리");
    memberMenu.add(new Menu("회원등록", new MemberAddListener(memberDao)));
    memberMenu.add(new Menu("회원목록", new MemberListListener(memberDao)));
    memberMenu.add(new Menu("회원조회", new MemberDetailListener(memberDao)));
    memberMenu.add(new Menu("회원변경", new MemberUpdateListener(memberDao)));
    memberMenu.add(new Menu("회원삭제", new MemberDeleteListener(memberDao)));
    mainMenu.add(memberMenu);

    MenuGroup boardMenu = new MenuGroup("식단일기");
    boardMenu.add(new Menu("식단등록", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("식단목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("식단조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("식단변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("식단삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);

    Menu helloMenu = new Menu("득근!");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);
  }
}
