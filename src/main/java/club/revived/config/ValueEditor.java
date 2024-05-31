package club.revived.config;

public interface ValueEditor<V>{
    V edit(final V currentValue);
}
