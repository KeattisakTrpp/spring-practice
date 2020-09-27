package practice.spring.models.index;


public class IndexSingleTon {
    private static IndexSingleTon Instance;

    public ReviewIndex invertedIndex = new ReviewIndex();

    // private constructor
    private IndexSingleTon () { }

    public static IndexSingleTon getInstance() {
        // checks if instance already created
        if (Instance == null) {
            // if not create an instance
            Instance = new IndexSingleTon();
        }
        // return the created instance on all subsequent calls
        return Instance;
    }

    public void setInvertedIndex(ReviewIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public ReviewIndex getInvertedIndex() {
        return this.invertedIndex;
    }
}
