package zio.blocks.schema

object VariantSimpleEnum extends App {
  sealed trait Payment
  object Payment {
    case object Cash extends Payment {
      lazy implicit val schema: Schema[Cash.type] = Schema.derived
    }
    case object CreditCard extends Payment {
      lazy implicit val schema: Schema[CreditCard.type] = Schema.derived
    }
    lazy implicit val schema: Schema[Payment] = Schema.derived
  }
  final case class Person(id: String, payment: Payment)
  object Person extends CompanionOptics[Person] {
    lazy implicit val schema: Schema[Person] = Schema.derived
    val id: Lens[Person, String]             = field(_.id)
//    val payment: Lens[Person, Payment]       = field(_.payment)
  }

  val dv = Person.schema.toDynamicValue(Person("1", Payment.Cash))
  println(s"dv: $dv")

  /*
  [error] Exception in thread "main" java.lang.ClassCastException: class zio.blocks.schema.VariantSimpleEnum$Cash$
  cannot be cast to class scala.runtime.BoxedUnit (zio.blocks.schema.VariantSimpleEnum$Cash$ and
  scala.runtime.BoxedUnit are in unnamed module of loader 'app')
   */

}
