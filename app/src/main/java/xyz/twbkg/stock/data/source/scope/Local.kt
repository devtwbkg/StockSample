package twbkg.xyz.stock.data.source.scope

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Qualifier

/**
 * This scope has been created for Dagger to differentiate between types of Data Sources
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class Local
