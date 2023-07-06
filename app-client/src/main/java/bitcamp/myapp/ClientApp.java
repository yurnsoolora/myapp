package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardNetworkDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.MemberNetworkDao;
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
import bitcamp.net.RequestEntity;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class ClientApp {

  Socket socket;
  DataOutputStream out;
  DataInputStream in;

  MemberDao memberDao;
  BoardDao boardDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {

    this.socket = new Socket(ip, port);
    this.out = new DataOutputStream(socket.getOutputStream());
    this.in = new DataInputStream(socket.getInputStream());

    this.memberDao = new MemberNetworkDao("member", in, out);
    this.boardDao = new BoardNetworkDao("board", in, out);

    prepareMenu();
  }

  public void close() throws Exception {
    prompt.close();
    out.close();
    in.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("실행 예) java ... bitcamp.myapp.ClientApp 서버주소 포트번호");
      return;
    }

    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
    app.close();
  }

  static void printTitle() {
    System.out.println("나의 식단 관리 시스템");
    System.out.println("----------------------------------");
  }

  public void execute() {
    printTitle();
    mainMenu.execute(prompt);

    try {
      out.writeUTF(new RequestEntity().command("quit").toJson());

    } catch (Exception e) {
      System.out.println("종료 오류!");
      e.printStackTrace();
    }
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