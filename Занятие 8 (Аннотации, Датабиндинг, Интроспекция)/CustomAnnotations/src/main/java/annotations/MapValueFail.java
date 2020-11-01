package annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapValueFail {}
