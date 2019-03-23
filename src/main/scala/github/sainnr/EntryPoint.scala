package github.sainnr

import com.typesafe.scalalogging.Logger
import github.sainnr.wsdl.nl.CountryInfoService

/** Oversimplified usage of imported WSDL classes. */
object EntryPoint extends App {

  val service = new CountryInfoService().getCountryInfoServiceSoap
  val res = service.listOfContinentsByName()
  res.getTContinent.forEach(c => Logger(this.getClass).info(c.getSName))
}
