package com.qlf.plants.bean;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class PlantBean {

	private String bloom_color;//������ɫ
	private String leaf_color;//Ҷ����ɫ
	private String plant_type;//ֲ������
	private String spec_features;//ֲ������
	private String plant_shape;//��״
	private String bloom_time;//����ʱ��
	private String common_name;//����
	private String genus_name;//������
	private String img;//����ͼ
	private String blooming;//�������
	private String subspecies_name;//����
	private String species_name;//����s
	
	private Drawable image;
	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable bitmap) {
		this.image = bitmap;
	}
	/**
	 * ���˻���
	 */
	private String fertilizer;//����ֵ
	private String sun;//����ֵ
	private String water;//ˮ��ֵ
	private String temperature_max_celsius;//�¶����ֵ
	private String temperature_min_celsius;//�¶����ֵ
	
	/**
	 * ��ֳ��֪
	 */
	private String description_source;//��ֳ�Ѷ�
	private String description_text;//����
	private String fertilizer_type;//ʹ�÷���
	private String growth;//�ɳ�����
	private String interesting;//Ȥ��
	private String planting;//��ֲ
	private String soil_irr;//�����͹��
	private String pruning;//�޼�
	private String pests;//����
	
	/**
	 * ������
	 */
	private String hardiness_zone_max;
	private String hardiness_zone_max_value;
	private String hardiness_zone_min;
	private String hardiness_zone_min_value;
	
	/**
	 * ���ȷ�Χ
	 */
	private String heat_zone_max;
	private String heat_zone_max_value;
	private String heat_zone_min;
	private String heat_zone_min_value;
	
	/**
	 * ��չ����
	 */
	private String spread_max;
	private String spread_min;
	/**
	 * �߶ȷ�Χ
	 */
	private String height_min;
	private String height_max;
	
	
	public String getSubspecies_name() {
		return subspecies_name;
	}

	public void setSubspecies_name(String subspecies_name) {
		this.subspecies_name = subspecies_name;
	}

	public String getSpecies_name() {
		return species_name;
	}

	public void setSpecies_name(String species_name) {
		this.species_name = species_name;
	}

	public PlantBean() {
	}
	
	public String getHeat_zone_max() {
		return heat_zone_max;
	}

	public void setHeat_zone_max(String heat_zone_max) {
		this.heat_zone_max = heat_zone_max;
	}

	public String getHeat_zone_max_value() {
		return heat_zone_max_value;
	}

	public void setHeat_zone_max_value(String heat_zone_max_value) {
		this.heat_zone_max_value = heat_zone_max_value;
	}

	public String getHeat_zone_min() {
		return heat_zone_min;
	}

	public void setHeat_zone_min(String heat_zone_min) {
		this.heat_zone_min = heat_zone_min;
	}

	public String getHeat_zone_min_value() {
		return heat_zone_min_value;
	}

	public void setHeat_zone_min_value(String heat_zone_min_value) {
		this.heat_zone_min_value = heat_zone_min_value;
	}

	public String getBloom_color() {
		return bloom_color;
	}
	public void setBloom_color(String bloom_color) {
		this.bloom_color = bloom_color;
	}
	public String getLeaf_color() {
		return leaf_color;
	}
	public void setLeaf_color(String leaf_color) {
		this.leaf_color = leaf_color;
	}
	public String getPlant_type() {
		return plant_type;
	}
	public void setPlant_type(String plant_type) {
		this.plant_type = plant_type;
	}
	public String getSpec_features() {
		return spec_features;
	}
	public void setSpec_features(String spec_features) {
		this.spec_features = spec_features;
	}
	public String getPlant_shape() {
		return plant_shape;
	}
	public void setPlant_shape(String plant_shape) {
		this.plant_shape = plant_shape;
	}
	public String getBloom_time() {
		return bloom_time;
	}
	public void setBloom_time(String bloom_time) {
		this.bloom_time = bloom_time;
	}
	public String getCommon_name() {
		return common_name;
	}
	public void setCommon_name(String common_name) {
		this.common_name = common_name;
	}
	public String getGenus_name() {
		return genus_name;
	}
	public void setGenus_name(String genus_name) {
		this.genus_name = genus_name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getBlooming() {
		return blooming;
	}
	public void setBlooming(String blooming) {
		this.blooming = blooming;
	}
	public String getFertilizer() {
		return fertilizer;
	}
	public void setFertilizer(String fertilizer) {
		this.fertilizer = fertilizer;
	}
	public String getSun() {
		return sun;
	}
	public void setSun(String sun) {
		this.sun = sun;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public String getTemperature_max_celsius() {
		return temperature_max_celsius;
	}
	public void setTemperature_max_celsius(String temperature_max_celsius) {
		this.temperature_max_celsius = temperature_max_celsius;
	}
	public String getTemperature_min_celsius() {
		return temperature_min_celsius;
	}
	public void setTemperature_min_celsius(String temperature_min_celsius) {
		this.temperature_min_celsius = temperature_min_celsius;
	}
	public String getDescription_source() {
		return description_source;
	}
	public void setDescription_source(String description_source) {
		this.description_source = description_source;
	}
	public String getDescription_text() {
		return description_text;
	}
	public void setDescription_text(String description_text) {
		this.description_text = description_text;
	}
	public String getFertilizer_type() {
		return fertilizer_type;
	}
	public void setFertilizer_type(String fertilizer_type) {
		this.fertilizer_type = fertilizer_type;
	}
	public String getGrowth() {
		return growth;
	}
	public void setGrowth(String growth) {
		this.growth = growth;
	}
	public String getInteresting() {
		return interesting;
	}
	public void setInteresting(String interesting) {
		this.interesting = interesting;
	}
	public String getPlanting() {
		return planting;
	}
	public void setPlanting(String planting) {
		this.planting = planting;
	}
	public String getSoil_irr() {
		return soil_irr;
	}
	public void setSoil_irr(String soil_irr) {
		this.soil_irr = soil_irr;
	}
	public String getPruning() {
		return pruning;
	}
	public void setPruning(String pruning) {
		this.pruning = pruning;
	}
	public String getPests() {
		return pests;
	}
	public void setPests(String pests) {
		this.pests = pests;
	}
	public String getHardiness_zone_max() {
		return hardiness_zone_max;
	}
	public void setHardiness_zone_max(String hardiness_zone_max) {
		this.hardiness_zone_max = hardiness_zone_max;
	}
	public String getHardiness_zone_max_value() {
		return hardiness_zone_max_value;
	}
	public void setHardiness_zone_max_value(String hardiness_zone_max_value) {
		this.hardiness_zone_max_value = hardiness_zone_max_value;
	}
	public String getHardiness_zone_min() {
		return hardiness_zone_min;
	}
	public void setHardiness_zone_min(String hardiness_zone_min) {
		this.hardiness_zone_min = hardiness_zone_min;
	}
	public String getHardiness_zone_min_value() {
		return hardiness_zone_min_value;
	}
	public void setHardiness_zone_min_value(String hardiness_zone_min_value) {
		this.hardiness_zone_min_value = hardiness_zone_min_value;
	}
	public String getSpread_max() {
		return spread_max;
	}
	public void setSpread_max(String spread_max) {
		this.spread_max = spread_max;
	}
	public String getSpread_min() {
		return spread_min;
	}
	public void setSpread_min(String spread_min) {
		this.spread_min = spread_min;
	}
	public String getHeight_min() {
		return height_min;
	}
	public void setHeight_min(String height_min) {
		this.height_min = height_min;
	}
	public String getHeight_max() {
		return height_max;
	}
	public void setHeight_max(String height_max) {
		this.height_max = height_max;
	}
	
	
}
