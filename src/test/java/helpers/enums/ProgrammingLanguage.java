package helpers.enums;

public enum ProgrammingLanguage {
    JAVA("Java"),
    PYTHON("Python"),
    CPP("C++"),
    C_SHARP("C#"),
    PASCAL("Pascal"),
    ASSEMBLER("Assembler"),
    PHP("PHP"),
    BASIC("Basic"),
    SCALA("Scala"),
    GOLANG("Golang");

    private final String value;

    ProgrammingLanguage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
