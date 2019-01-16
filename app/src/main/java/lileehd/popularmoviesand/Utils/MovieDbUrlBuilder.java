package lileehd.popularmoviesand.Utils;

public class MovieDbUrlBuilder {
    private String defaultUrl;

    private String sort = null;


    public MovieDbUrlBuilder() {
        defaultUrl = "https://api.themoviedb.org/3/movie/[SORTED]?api_key=[API_KEY]"
                .replace("[API_KEY]", "e9a4d258ab2f1609f16bd29d0eef3719");
    }

    public String getUrl() {
        String sort;
        if (this.sort == null) {
            sort = "popular";
        } else {
            sort = this.sort;
        }

        String url = defaultUrl.replace("[SORTED]", sort);
        this.sort = null;
        return url;

    }

    public MovieDbUrlBuilder sortBy(String sort) {
        this.sort = sort;
        return this;
    }
}
