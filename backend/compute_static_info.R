
MIDA_COTXE <- 4

library(jsonlite)

options(digits=2)

json <- fromJSON("cid.json", flatten=TRUE)
json$mida <- sapply(1:nrow(json), function(i) {
  return (max(json$ALCADA[i], json$AMPLADA[i]))
})
json$nplaces <- floor(json$mida / MIDA_COTXE)

cat(toJSON(json, digits=8), file = 'cid-infostat.json')

