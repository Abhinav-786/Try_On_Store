package info.example.tryonstore.recyclerone;

public class Album{
    private String Title;
    private String Description;
    private String Prize;
    private String Image;

    public Album() {
    }

    public Album(String Title, String Description, String Prize, String Image) {
        this.Title = Title;
        this.Description = Description;
        this.Prize = Prize;
        this.Image=Image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrize() {
        return Prize;
    }

    public void setPrize(String prize) {
        Prize = prize;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
