package mk.ukim.finki.wp.sep2022.model;

public class MatchLocation {

    public MatchLocation() {
    }

    public MatchLocation(String name) {
        this.name = name;
    }

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
