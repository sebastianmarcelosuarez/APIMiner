package model;

public enum BoxStatus {
//TODO a QUESTION_MARK  may be implemented
    NONE,
    FLAG;


    public BoxStatus getNext() {
        return this.ordinal() < BoxStatus.values().length - 1
                ? BoxStatus.values()[this.ordinal() + 1]
                : null;
    }

}




