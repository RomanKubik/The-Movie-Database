package kubik.roman.moviesdb.models.movies_detailes;

import com.google.gson.annotations.SerializedName;

/**
 * Image specifications
 */
public class Image {
    @SerializedName("file_path")
    private String filePath;
    private int width;
    private int height;
    @SerializedName("iso_639_1")
    private Object iso;
    @SerializedName("aspect_ratio")
    private double aspectRatio;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Object getIso() {
        return iso;
    }

    public void setIso(Object iso) {
        this.iso = iso;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
