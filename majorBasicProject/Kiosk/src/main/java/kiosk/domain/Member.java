package kiosk.domain;

public class Member {
    // 회원 번호, 회원 아이디, 회원 비밀번호, 적립 잔 수
    String memberNum;
    String Id;
    String pawd;
    int savedCup;

    public Member(String memberNum, String id, String pawd, int savedCup) {
        this.memberNum = memberNum;
        this.Id = id;
        this.pawd = pawd;
        this.savedCup = savedCup;
    }

    //getter & setter
    public String getMemberNum() {
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

    @Override
    public String toString() {
        return String.format("%s %s %s %d", memberNum, Id, pawd, savedCup);
    }

}