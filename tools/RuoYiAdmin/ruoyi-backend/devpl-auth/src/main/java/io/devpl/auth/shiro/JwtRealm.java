package io.devpl.auth.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.devpl.auth.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * JWT令牌校验
 * @author Xu Jiabao
 * @since 2022/3/18
 */
@Slf4j
public class JwtRealm extends AbstractRealm {

    // 数字签名密钥（不要将其返回前端）
    private static final String SECRET = "@:)Together For^&* A Shared Future{}:><:";
    // 随机数生成器，添加到生成的令牌中
    private static final Random random = new Random();
    // 令牌缓存，双重检查锁Double Check Lock + 单例模式创建缓存管理器
    // private static final Cache JWT_CACHE = CacheManager.create().getCache("jwt");

    public JwtRealm(AuthService identityService) {
        super(identityService);
    }

    /**
     * 身份认证，验证JWT的有效性。验证通过后解码payload放入user
     * @param token JwtToken
     * @return AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String jwt = (String) token.getPrincipal();
        // jwt格式错误时会抛出异常
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.decode(jwt);
        } catch (JWTDecodeException exception) {
            log.debug("JWT Realm: JWT解码异常");
            return null;
        }
        String userId = decodedJWT.getClaim("userId").asString();
        // 验证JWT是否有效，命中缓存则有效。不能使用isKeyInCache进行判断，即使缓存过期也会返回true
        /*
        if (!JWT_CACHE.isKeyInCache(userId)) {
            return null;
        }
        // */
        // Element element = JWT_CACHE.get(userId);  // 访问缓存，延长令牌有效时间
        // if (element == null || element.getObjectValue() == null) {
        //     return null;
        // }
        // 会出现令牌失效的BUG，这里使用put方法重新将jwt放入缓存
        // JWT_CACHE.put(new Element(userId, jwt));
        //
        // User user = new User();
        // // 取的方式与createToken()中放入的方式一致
        // user.setUserId(userId);
        // user.setUsername(decodedJWT.getClaim("username").asString());
        // user.setShortName(decodedJWT.getClaim("shortName").asString());
        // user.setAgencyId(decodedJWT.getClaim("agencyId").asString());
        // user.setAgencyName(decodedJWT.getClaim("agencyName").asString());
        // user.setUserType(UserType.valueOf(decodedJWT.getClaim("userType").asString()));
        // // 设置UserClass
        // // user.setUserClass(UserClass.valueOf(decodedJWT.getClaim("userClass").asString()));
        // // 第一个参数principal必须是user，因为其余很多方法已默认subject.getPrincipal取出的是user
        // return new SimpleAuthenticationInfo(user, jwt, getName());
        return null;
    }

    /**
     * 创建JWT令牌，后续访问时在请求头的Authorization附带jwt
     * 用户名密码登录成功，验证码登录成功，基础平台token验证通过调用本方法获取令牌
     * @param user 密码、手机验证码方式登录成功后从subject获取的user
     * @return jwt
     */
    public static String createToken(User user) {
        /*
        如果设置令牌有效期exp，那么点击退出登录后，无法让令牌失效。
        而且很可能用户在令牌失效后还在进行操作，此时又需要重新登录
        所以，生成令牌时添加一个随机数，让每次获取的令牌不一致，同时放入会过期的缓存中
        退出登录时会从缓存移除，每次调用被Filter拦截的接口时都会去缓存中取，可以刷新缓存的过期时间
         */
        // String token = JWT.create()
        //         .withClaim("userId", user.getUserId())
        //         .withClaim("username", user.getUsername())
        //         .withClaim("shortName", user.getShortName())
        //         .withClaim("agencyId", user.getAgencyId())
        //         .withClaim("agencyName", user.getAgencyName())
        //         .withClaim("userType", user.getUserType().name())
        //         // .withClaim("userClass", user.getUserClass().name())
        //         .withClaim("rand", random.nextInt(100000))
        //         // 设置过期时间
        //         // .withExpiresAt(new Date(System.currentTimeMillis() + ConfigProperties.JWT_EXPIRE_TIME))
        //         .sign(Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8)));
        // // Key：用户ID，Value：JWT。如果用户重复登录，之前的令牌被覆盖
        // JWT_CACHE.put(new Element(user.getUserId(), token));
        return "token";
    }

    /**
     * 退出登录时调用，移除JWT
     * @param userId 用户ID
     */
    public static boolean removeToken(String userId) {
        // return JWT_CACHE.remove(userId);
        return true;
    }

}
