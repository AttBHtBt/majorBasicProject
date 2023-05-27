package kiosk.domain;

public class Member {
   // 회원 번호, 회원 아이디, 회원 비밀번호, 적립 잔 수
    int memberNum;
    String Id;
    String pawd;
    int savedCup;

    public Member(int memberNum, String id, String pawd, int savedCup) {
        this.memberNum = memberNum;
        Id = id;
        this.pawd = pawd;
        this.savedCup = savedCup;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public String getId() {
        return Id;
    }

    public String getPawd() {
        return pawd;
    }

    public int getSavedCup() {
        return savedCup;
    }

    public void setSavedCup(int savedCup) {
        this.savedCup = savedCup;
    }
}
