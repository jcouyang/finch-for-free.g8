package $package$
package instances

import io.circe.{Encoder => CirceEncoder, Json}

object Encoder {
  // \$COVERAGE-OFF\$
  // address finch issue https://github.com/finagle/finch/issues/765
  implicit val encodeExceptionCirce: CirceEncoder[Exception] =
    CirceEncoder.instance(e =>
      Json.obj("message" -> Option(e.getMessage).fold(Json.Null)(Json.fromString)))
  // \$COVERAGE-ON\$
}
