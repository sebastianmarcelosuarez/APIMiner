package model;

import java.util.Random;

public class Box {

    Boolean mine;
    Boolean hidden;
    BoxStatus status;
    int minesAround;
    int posi;
    int posj;


    public Box (){
        super();
        this.hidden = true;
        this.status = BoxStatus.NONE;
        this.minesAround = 0 ;

        Random random = new Random();
        int result = random.nextInt(2);

        if (result == 0 ) {
            this.mine = Boolean.TRUE;
        } else {
            this.mine = Boolean.FALSE;
        }
    }

    public Boolean getMine() {
        return mine;
    }

    public void setMine(Boolean mine) {
        this.mine = mine;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public BoxStatus getStatus() {
        return status;
    }

    public void setStatus(BoxStatus status) {
        this.status = status;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    public int getPosj() {
        return posj;
    }

    public void setPosj(int posj) {
        this.posj = posj;
    }
}

