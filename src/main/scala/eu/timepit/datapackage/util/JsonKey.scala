package eu.timepit.datapackage.util

trait JsonKey[T] {
  def key: String
}

object JsonKey {
  def instance[T](k: String): JsonKey[T] = new JsonKey[T] {
    override def key: String = k
  }

  def keyOf[T](implicit jk: JsonKey[T]): String = jk.key
}
