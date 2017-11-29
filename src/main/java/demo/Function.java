package demo;

public interface Function<R, A> {
    public R callback(A... args);
}
