package yangdongjue.membershipservice.facade

interface ShopFacade {
    fun findShopTypeById(shopId: Long): String
}
