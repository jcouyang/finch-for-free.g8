package $package$
package controllers

import io.finch.Output
import io.finch._
import services._
import models._

object ServerInfoController {
  def get: ProgramF[Output[ServerInfo]] = {
    ServerInfoService.info map {
      case Right(body) => Ok(body)
      case Left(error) => InternalServerError(new Exception(error))
    }
  }
}
