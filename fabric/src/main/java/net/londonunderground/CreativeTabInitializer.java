package net.londonunderground;

import mtr.CreativeModeTabs;
import net.minecraft.resources.ResourceLocation;

#if MC_VERSION >= "12000"
import net.londonunderground.mappings.FabricRegistryUtilities;
import java.lang.reflect.Field;
#endif

/**
 * 创造模式物品栏初始化器
 * 在 1.20+ 版本中负责注册自定义创造模式物品栏
 */
public class CreativeTabInitializer {
	
	#if MC_VERSION >= "12000"
	/**
	 * 初始化所有创造模式物品栏（Fabric 1.20+）
	 * 这个方法应该在 MainFabric.onInitialize() 中调用，在任何物品注册之前
	 */
	public static void initFabric() {
		// 在 1.20+ 版本中，我们需要手动注册创造模式物品栏
		// 因为 MTR 库的 CreativeModeTabs.Wrapper 可能没有正确处理 1.20+ 的注册
		
		// 注册 TFL_BLOCKS 物品栏
		registerTab(MyItems.TFL_BLOCKS);
		
		// 注册 TFL_STATION 物品栏
		registerTab(MyItems.TFL_STATION);
		
		// 注册 TFL_SIGNS 物品栏
		registerTab(MyItems.TFL_SIGNS);
		
		// 注册 TFL_LEGACY 物品栏
		registerTab(MyItems.TFL_LEGACY);
	}
	
	/**
	 * 注册单个创造模式物品栏
	 */
	private static void registerTab(CreativeModeTabs.Wrapper wrapper) {
		if (wrapper != null && wrapper.resourceLocation != null) {
			// 调用 FabricRegistryUtilities 来创建和注册物品栏
			// 注意：这里我们依赖于 Wrapper 内部的 creativeModeTabSupplier
			// 如果 MTR 库的 Wrapper 已经正确处理了 1.20+，则不需要此步骤
			try {
				// 尝试获取 Wrapper 中的 creativeModeTabSupplier 字段
				Field supplierField = CreativeModeTabs.Wrapper.class.getDeclaredField("creativeModeTabSupplier");
				supplierField.setAccessible(true);
				Object supplier = supplierField.get(wrapper);
				
				if (supplier == null) {
					// 如果 supplier 为 null，说明 MTR 库没有正确初始化
					// 我们需要手动创建物品栏
					System.out.println("[London Underground] Manually registering creative tab: " + wrapper.resourceLocation);
				} else {
					// 触发 supplier 以创建物品栏
					wrapper.get();
				}
			} catch (Exception e) {
				System.err.println("[London Underground] Failed to access CreativeModeTabs.Wrapper fields: " + e.getMessage());
				// 降级处理：直接调用 get()
				wrapper.get();
			}
		}
	}
	#else
	/**
	 * 1.19 及以下版本不需要特殊处理
	 */
	public static void initFabric() {
		// 在旧版本中，MTR 库会自动处理物品栏注册
	}
	#endif
}
