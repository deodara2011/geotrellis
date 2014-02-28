package geotrellis
import geotrellis.spark.formats.ArgWritable
import geotrellis.spark.formats.TileIdWritable
import geotrellis.spark.metadata.Context
import geotrellis.spark.rdd.RasterRDD
import geotrellis.spark.rdd.SaveRasterFunctions

import org.apache.hadoop.fs.Path
import org.apache.spark.rdd.RDD

package object spark {

  type TileIdRaster = (Long, Raster)
  type TileIdCoordRaster = (Long, Long, Long, Raster)
  type TileIdArgWritable = (TileIdWritable, ArgWritable)

  implicit class SavableRasterWritable(val raster: RDD[TileIdArgWritable]) {
    def save(path: Path) = SaveRasterFunctions.save(raster, path)
  }

  implicit class MakeRasterRDD(val prev: RDD[TileIdRaster]) {
    def withContext(ctx: Context) = new RasterRDD(prev, ctx)
  }
  implicit class SavableRasterRDD(val rdd: RasterRDD) {
    def save(path: Path) = SaveRasterFunctions.save(rdd, path)
  }
}