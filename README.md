SpiceAnnotations
================

This is a simple app showing how to use both AndroidAnnotations and RoboSpice frameworks in one
project with no complicated call nor declaration.

Create AndroidAnnotations REST client like this:

```java
@Rest(rootUrl = "https://api.github.com/", converters = {...})
public interface RestClient {

    @Get("users/{username}")
    User getUser(@Path CharSequence username);

}
```

Do RoboSpice request like this:

```java
    spiceManager.executeRequest(User.class, r -> r.getUser(username),
            username, // cache key
            DurationInMillis.ALWAYS_RETURNED, // cache duration
            this); // listener
    // or simply (no cache)
    spiceManager.executeRequest(User.class, r -> r.getUser(username), listener);
```
