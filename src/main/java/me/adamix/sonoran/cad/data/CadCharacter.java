package me.adamix.sonoran.cad.data;

import me.adamix.sonoran.codec.Codec;
import me.adamix.sonoran.codec.StructCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Note: There is still some stuff missing. Like "data" and "options" fields in Field.
public record CadCharacter(
		long recordTypeId,
		long id,
		@Nullable String syncUniqueId,
		@Nullable String syncId,
		@NotNull String name,
		@Nullable String image,
		long type,
		List<Section> sections
) {
	public static final Codec<CadCharacter> CODEC = StructCodec.struct(
			"recordTypeId", Codec.LONG, CadCharacter::recordTypeId,
			"id", Codec.LONG, CadCharacter::id,
			"syncUniqueId", Codec.STRING.optional(), CadCharacter::syncUniqueId,
			"syncId", Codec.STRING.optional(), CadCharacter::syncId,
			"name", Codec.STRING, CadCharacter::name,
			"img", Codec.STRING.optional(), CadCharacter::image,
			"type", Codec.LONG, CadCharacter::type,
			"sections", Section.CODEC.list(), CadCharacter::sections,
			CadCharacter::new
	);

	public record Section(
			int category,
			@NotNull String label,
			@NotNull List<Field> fields,
			boolean searchCiv,
			boolean searchVeh,
			boolean enableDuplicate,
			boolean isDuplicate,
			@NotNull Dependency dependency
	) {
		public static final Codec<Section> CODEC = StructCodec.struct(
				"category", Codec.INT, Section::category,
				"label", Codec.STRING, Section::label,
				"fields", Field.CODEC.list(), Section::fields,
				"searchCiv", Codec.BOOLEAN, Section::searchCiv,
				"searchVeh", Codec.BOOLEAN, Section::searchVeh,
				"enableDuplicate", Codec.BOOLEAN, Section::enableDuplicate,
				"isDuplicate", Codec.BOOLEAN, Section::isDuplicate,
				"dependency", Dependency.CODEC, Section::dependency,
				Section::new
		);

		public record Field(
				@NotNull String type,
				@NotNull String label,
				@NotNull String value,
				int size,
				boolean isPreviewed,
				boolean isSupervisor,
				boolean isRequired,
				boolean unique,
				boolean readOnly,
				@Nullable String mask,
				boolean maskReverse,
				boolean dbMap,
				boolean isFromSync,
				@NotNull String uid,
				@NotNull Dependency dependency
		) {
			// ToDO Add fields for "data" and "options"
			public static final Codec<Field> CODEC = StructCodec.struct(
					"type", Codec.STRING, Field::type,
					"label", Codec.STRING, Field::label,
					"value", Codec.STRING, Field::value,
					"size", Codec.INT, Field::size,
					"isPreviewed", Codec.BOOLEAN, Field::isPreviewed,
					"isSupervisor", Codec.BOOLEAN, Field::isSupervisor,
					"isRequired", Codec.BOOLEAN, Field::isRequired,
					"unique", Codec.BOOLEAN, Field::unique,
					"readOnly", Codec.BOOLEAN, Field::readOnly,
					"mask", Codec.STRING.optional(), Field::mask,
					"maskReverse", Codec.BOOLEAN, Field::maskReverse,
					"dbMap", Codec.BOOLEAN, Field::dbMap,
					"isFromSync", Codec.BOOLEAN, Field::isFromSync,
					"uid", Codec.STRING, Field::uid,
					"dependency", Dependency.CODEC, Field::dependency,
					Field::new
			);
		}

		public record Dependency(
				@Nullable String type,
				@Nullable String fid,
				@Nullable List<String> acceptableValues
		) {
			public static final Codec<Dependency> CODEC = StructCodec.struct(
					"type", Codec.STRING.optional(), Dependency::type,
					"fid", Codec.STRING.optional(), Dependency::fid,
					"acceptableValues", Codec.STRING.list().optional(), Dependency::acceptableValues,
					Dependency::new
			);
		}
	}
}
